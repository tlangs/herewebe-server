package org.langsford.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.langsford.dto.Pokemon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by trevyn on 1/11/15.
 */
@Controller
public class PokePictureController {

    private Map<String, int[]> regions;
    private Map<String, Pokemon> pokemonNameMap = new HashMap<>();
    private Map<Integer, Pokemon> pokemonIdMap = new HashMap<>();
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

            this.font = Font.createFont(Font.TRUETYPE_FONT, PokePictureController.class.getResourceAsStream("/fonts/PokemonSolid.ttf"));
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


    @RequestMapping(value = "/poke", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody
    public byte[] greeting(@RequestParam(value = "name", required = true, defaultValue = "") String name,
                           @RequestParam(value = "pokemon", required = true, defaultValue = "") String pokemon,
                           @RequestParam(value = "regions", required = true, defaultValue = "") String regions) {

        Pokemon retrievedPokemon;
        BufferedImage retrievedSprite;
        if ((retrievedPokemon = this.pokeCache.get(pokemon)) == null) {
            retrievedPokemon = this.getPokemon(pokemon, regions);

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
            JSONObject spriteInfo = getJSON("http://pokeapi.co/" + spritesArray.getJSONObject(0).getString("resource_uri"));
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
        int rgb = image.getRGB(1,1);

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        int fontht = fontMetrics.getHeight();
        g.drawString(name, 50, (bottomOfSprite * 3) + fontht);
        g.dispose();
        int bottomOfText = this.getBottomOfText(image);
        if (name.equals("")) {
            image = image.getSubimage(0, 0, image.getWidth(), sprite.getHeight() * 3);
        } else {
            image = image.getSubimage(0, 0, image.getWidth(), bottomOfText + 25);

        }
        return image;
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
        lineStart = lineEnd + 1;
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