package blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by trevyn on 4/5/14.
 */
@Controller
@RequestMapping("api/blog")
public class BlogController {

    @RequestMapping(value = "/getpost", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public BlogPost getBlogPost(@RequestParam(value = "id", required = true) int id) {

        BlogPost postnull = new BlogPost();
        postnull.setAuthor("");
        postnull.setDate(new Date().toString());
        postnull.setId(-1);
        postnull.setTitle("");
        postnull.setContent("");

        BlogPost post0 = new BlogPost();
        post0.setAuthor("Trevyn Langsford");
        post0.setDate(new Date().toString());
        post0.setId(0);
        post0.setTitle("Test Post 0");
        post0.setContent("This is the content of the post. It is a cool post isn't it.");

        BlogPost post1 = new BlogPost();
        post1.setAuthor("Trevyn Langsford");
        post1.setDate(new Date().toString());
        post1.setId(1);
        post1.setTitle("Test Post 1");
        post1.setContent("This is the content of the post. It is a cool post isn't it.");

        BlogPost post2 = new BlogPost();
        post2.setAuthor("Trevyn Langsford");
        post2.setDate(new Date().toString());
        post2.setId(2);
        post2.setTitle("Test Post 2");
        post2.setContent("This is the content of the post. It is a cool post isn't it.");

        List<BlogPost> posts = Arrays.asList(post0, post1, post2);

        for (BlogPost p : posts) {
            if (p.id == id) {
                return p;
            }
        }

        return postnull;
    }

    @RequestMapping(value = "/getposts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<BlogPost> getBlogPosts(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        return Arrays.asList();
    }


}
