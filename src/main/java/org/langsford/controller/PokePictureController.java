package org.langsford.controller;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.langsford.dto.Pokemon;
import org.langsford.dto.PokemonDisplay;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by trevyn on 1/11/15.
 */
@Controller
@RequestMapping("/poke")
public class PokePictureController {

    private Map<String, int[]> regions;
    private Map<String, Pokemon> pokemonNameMap = new HashMap<>();
    private TreeMap<Integer, Pokemon> pokemonIdMap = new TreeMap<>();
    private Map<String, Pokemon> pokeCache = new HashMap<>();
    private Font font;
    private BufferedImage missingNo;

    public PokePictureController() {
        int[] kanto = new int[151];
        for (int i = 1; i <= 151; i++) {
            kanto[i - 1] = i;
        }

        int[] johto = new int[100];
        for (int i = 152; i <= 251; i++) {
            johto[i - 152] = i;
        }

        int[] hoenn = new int[135];
        for (int i = 252; i <= 386; i++) {
            hoenn[i - 252] = i;
        }
        int[] sinnoh = new int[107];
        for (int i = 387; i <= 493; i++) {
            sinnoh[i - 387] = i;
        }

        int[] unova = new int[156];
        for (int i = 494; i <= 649; i++) {
            unova[i - 494] = i;
        }

        int[] kalos = new int[71];
        for (int i = 650; i <= 720; i++) {
            kalos[i - 650] = i;
        }

        Map<String, int[]> map = new HashMap<>();
        map.put("kanto", kanto);
        map.put("johto", johto);
        map.put("hoenn", hoenn);
        map.put("sinnoh", sinnoh);
        map.put("unova", unova);
        map.put("kalos", kalos);

        this.regions = map;

        try {

            this.font = Font.createFont(Font.TRUETYPE_FONT,
                    PokePictureController.class.getResourceAsStream("/fonts/PokemonSolid.ttf"));
            this.missingNo = ImageIO.read(PokePictureController.class.getResourceAsStream("/Missingno_RB.png"));
            JSONObject response = getJSON("http://pokeapi.co/api/v1/pokedex/1");
            JSONArray a = response.getJSONArray("pokemon");
            for (int i = 0; i < a.length(); i++) {
                Pokemon poke = new Pokemon();
                poke.setName(a.getJSONObject(i).getString("name"));
                String resourceURI = a.getJSONObject(i).getString("resource_uri");
                poke.setResourceURI(resourceURI);
                String[] elements = resourceURI.split("/");
                poke.setNationalId(Integer.parseInt(elements[elements.length - 1]));
                this.pokemonNameMap.put(poke.getName(), poke);
                this.pokemonIdMap.put(poke.getNationalId(), poke);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pokePage() {
        ModelAndView mav = new ModelAndView("poke");
        List<PokemonDisplay> pokeNames = new ArrayList<>();
        for (Map.Entry<Integer, Pokemon> entry : this.pokemonIdMap.entrySet()) {
            PokemonDisplay pd = new PokemonDisplay();
            pd.setName(WordUtils.capitalize(entry.getValue().getName(), ' ', '-'));
            pd.setValue(entry.getValue().getName());
            pokeNames.add(pd);
        }
        mav.addObject("pokemonList", pokeNames);
        return mav;
    }

    @RequestMapping(value="/fileupload", method=RequestMethod.POST, produces = "application/zip")
    public @ResponseBody byte[] handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                                 HttpServletResponse response) {
        byte[] output = new byte[0];
        if (!file.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                output = bulkUpload(sb.toString(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName = file.getOriginalFilename().split("\\.")[0];
        response.setHeader("Content-Disposition", "filename=" + fileName + ".zip");
        return output;
    }

    @RequestMapping(value = "bulkupload", method = RequestMethod.GET, produces = "application/zip")
    @ResponseBody
    public byte[] bulkUpload(@RequestParam String bulkInput, HttpServletResponse response) {
        List<MemoryFile> files = new ArrayList<>();
        byte[] output = new byte[0];
        try {
            JSONArray array = new JSONArray(URLDecoder.decode(bulkInput, "UTF-8"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.has("name") ? object.getString("name") : "";
                String pokemon = object.has("pokemon") ? object.getString("pokemon") : "";
                String regions = object.has("regions") ? object.getString("regions") : "";
                byte[] barray = this.pokeImage(name, pokemon, regions);
                MemoryFile mfile = new MemoryFile();
                mfile.contents = barray;
                mfile.fileName = (name.equals("") ? i : name) + ".png";
                files.add(mfile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            output = createZipByteArray(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "filename=bulk.zip");
        return output;
    }

    private static class MemoryFile {
        public String fileName;
        public byte[] contents;
    }

    private byte[] createZipByteArray(List<MemoryFile> memoryFiles) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        List<String> filenames = new ArrayList<>();
        try {
            for (MemoryFile memoryFile : memoryFiles) {
                ZipEntry zipEntry;
                if (!filenames.contains(memoryFile.fileName)) {
                    zipEntry = new ZipEntry(memoryFile.fileName);
                } else {
                    zipEntry = new ZipEntry(
                            Collections.frequency(filenames, memoryFile.fileName) + memoryFile.fileName
                    );
                }
                filenames.add(memoryFile.fileName);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(memoryFile.contents);
                zipOutputStream.closeEntry();
            }
        } finally {
            zipOutputStream.close();
        }
        return byteArrayOutputStream.toByteArray();
    }


    @RequestMapping(value = "/makepokemon", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] pokeImage(@RequestParam(value = "name", required = true, defaultValue = "") String name,
                            @RequestParam(value = "pokemon", required = true, defaultValue = "") String pokemon,
                            @RequestParam(value = "regions", required = true, defaultValue = "") String regions) {

        Pokemon retrievedPokemon;
        BufferedImage retrievedSprite;
        pokemon = pokemon.toLowerCase().trim();
        if ((retrievedPokemon = this.pokeCache.get(pokemon)) == null) {
            retrievedPokemon = this.getPokemon(pokemon, regions);
        }
        if (retrievedPokemon != null && retrievedPokemon.getNationalId() > 10000) {
            return pokeImage(name, pokemon.split("-")[0], regions);
        }
        if (retrievedPokemon == null) {
            retrievedSprite = this.missingNo;
        } else {
            retrievedSprite = this.getSprite(retrievedPokemon);
        }
        if (retrievedPokemon != null && retrievedSprite != null && !this.pokeCache.containsKey(pokemon)) {
            this.pokeCache.put(retrievedPokemon.getName(),
                    new Pokemon(
                            retrievedPokemon.getNationalId(),
                            retrievedPokemon.getName(),
                            retrievedPokemon.getResourceURI(),
                            retrievedSprite));
        }
        BufferedImage returnImage = makeImage(retrievedSprite, name);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(returnImage, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    private Pokemon getPokemon(String pokemon, String regions) {
        int pokeId;
        Pokemon retrievedPokemon;
        if (pokemon.equals("")) {
            ArrayList<Integer> ids = new ArrayList<>(720);
            if (regions.equals("")) {
                for (int[] region : this.regions.values()) {
                    for (int id : region) {
                        ids.add(id);
                    }
                }
            } else {
                String[] selectedRegions = regions.split(",");
                for (String region : selectedRegions) {
                    int[] regionList;
                    if ((regionList = this.regions.get(region)) != null) {
                        for (int id : regionList) {
                            ids.add(id);
                        }
                    }
                }
                if (ids.size() == 0) {
                    for (int id : this.regions.get("kanto")) {
                        ids.add(id);
                    }
                }
            }
            ids.trimToSize();
            pokeId = ids.get((int) Math.floor(Math.floor(Math.random() * ids.size())));
            retrievedPokemon = pokemonIdMap.get(pokeId);
        } else {
            retrievedPokemon = pokemonNameMap.get(pokemon);
        }
        return retrievedPokemon;
    }

    private BufferedImage getSprite(Pokemon pokemon) {
        BufferedImage image = null;
        try {
            JSONObject pokemonEntry = getJSON("http://pokeapi.co/" + pokemon.getResourceURI());
            JSONArray spritesArray = pokemonEntry.getJSONArray("sprites");
            JSONObject spriteInfo = getJSON("http://pokeapi.co/" +
                    spritesArray.getJSONObject(0).getString("resource_uri"));
            image = ImageIO.read(new URL("http://pokeapi.co/" + spriteInfo.getString("image")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage makeImage(BufferedImage sprite, String name) {
        int bottomOfSprite = this.getBottomOfSprite(sprite);
        BufferedImage image = new BufferedImage(
                (sprite.getWidth() * 3) + 50, (sprite.getHeight() * 5),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        float size = 120F;
        g.setFont(this.font.deriveFont(size));
        FontMetrics fontMetrics = g.getFontMetrics();
        while (fontMetrics.stringWidth(name) > sprite.getWidth() * 3 - 50) {
            size--;
            g.setFont(font.deriveFont(size));
            fontMetrics = g.getFontMetrics();
        }
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.drawImage(sprite, 25, 0, sprite.getWidth() * 3, sprite.getHeight() * 3, null);
        g.setColor(Color.BLACK);
        int rgb = image.getRGB(1, 1);

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        int fontht = fontMetrics.getHeight();
        g.drawString(name, 50, (bottomOfSprite * 3) + fontht);
        g.dispose();

        return cropOutWhitespace(image);
    }

    private BufferedImage cropOutWhitespace(BufferedImage image) {
        int firstCol = firstColumnOfImage(image);
        int firstRow = firstRowOfImage(image);
        int lastCol = lastColumnOfImage(image);
        int lastRow = lastRowOfImage(image);

        return image.getSubimage(firstCol, firstRow, lastCol - firstCol, lastRow - firstRow);
    }


    private int firstRowOfImage(BufferedImage image) {
        int lineStart;
        for (lineStart = 0; lineStart < image.getHeight(); lineStart++) {
            boolean stop = false;
            for (int column = 0; column < image.getWidth(); column++) {
                if (image.getRGB(column, lineStart) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        return lineStart;
    }

    private int lastRowOfImage(BufferedImage image) {
        int lineEnd;
        for (lineEnd = image.getHeight() - 1; lineEnd > 0; lineEnd--) {
            boolean stop = false;
            for (int column = 0; column < image.getWidth(); column++) {
                if (image.getRGB(column, lineEnd) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        return lineEnd + 1;
    }

    private int firstColumnOfImage(BufferedImage image) {
        int columnStart;
        for (columnStart = 0; columnStart < image.getWidth(); columnStart++) {
            boolean stop = false;
            for (int row = 0; row < image.getHeight(); row++) {
                if (image.getRGB(columnStart, row) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) break;
        }
        return columnStart;
    }

    private int lastColumnOfImage(BufferedImage image) {
        int columnEnd;
        for (columnEnd = image.getWidth() - 1; columnEnd > 0; columnEnd--) {
            boolean stop = false;
            for (int row = 0; row < image.getHeight(); row++) {
                if (image.getRGB(columnEnd, row) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) break;
        }
        return columnEnd + 1;
    }

    private int getBottomOfText(BufferedImage image) {
        int lineStart;
        for (lineStart = 0; lineStart < image.getHeight(); lineStart++) {
            boolean stop = false;
            for (int column = 0; column < image.getWidth(); column++) {
                if (image.getRGB(column, lineStart) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        int lineEnd;
        for (lineEnd = lineStart; lineEnd < image.getHeight(); lineEnd++) {
            List<Integer> rgbs = new ArrayList<>();
            for (int column = 0; column < image.getWidth(); column++) {
                rgbs.add(image.getRGB(column, lineEnd));
            }
            int min = Collections.min(rgbs);
            if (min == -1) {
                break;
            }
        }
        for (lineStart = lineEnd + 1; lineStart < image.getHeight(); lineStart++) {
            boolean stop = false;
            for (int column = 0; column < image.getWidth(); column++) {
                if (image.getRGB(column, lineStart) != -1) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        for (lineEnd = lineStart; lineEnd < image.getHeight(); lineEnd++) {
            List<Integer> rgbs = new ArrayList<>();
            for (int column = 0; column < image.getWidth(); column++) {
                rgbs.add(image.getRGB(column, lineEnd));
            }
            int min = Collections.min(rgbs);
            if (min == -1) {
                break;
            }
        }

        return lineEnd;
    }

    private int getBottomOfSprite(BufferedImage sprite) {
        int lineStart;
        for (lineStart = 0; lineStart < sprite.getHeight(); lineStart++) {
            boolean stop = false;
            for (int column = 0; column < sprite.getWidth(); column++) {
                if (sprite.getRGB(column, lineStart) != 0) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        int lineEnd;
        for (lineEnd = lineStart; lineEnd < sprite.getHeight(); lineEnd++) {
            List<Integer> rgbs = new ArrayList<>();
            for (int column = 0; column < sprite.getWidth(); column++) {
                rgbs.add(sprite.getRGB(column, lineEnd));
            }
            int min = Collections.min(rgbs);
            if (min == 0) {
                break;
            }
        }
        return lineEnd;
    }

    private static JSONObject getJSON(String url) {
        JSONObject json = new JSONObject();
        try {
            URL urlToGet = new URL(url);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlToGet.openStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            json = new JSONObject(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}