package threewks.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import threewks.controller.dto.IndexMeta;
import threewks.testinfra.BaseControllerTest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest extends BaseControllerTest {

    @Override
    protected Object controller() {
        return new IndexController("APP NAME");
    }

    @Test
    public void indexPage() throws Exception {
        indexPageSuccess("/path");
    }

    @Test
    public void indexPage_willMatchSlash() throws Exception {
        indexPageSuccess("/");
    }

    @Test
    public void indexPage_willMatchMultiLevel() throws Exception {
        indexPageSuccess("/path/to/some/thing/");
    }

    private void indexPageSuccess(String url) throws Exception {
        IndexMeta expected = new IndexMeta()
            .setTitle("APP NAME")
            .setUrl(url)
            .setDescription("Try APP NAME today!")
            .setImage("https://lh3.googleusercontent.com/sm_h6TfeNXSFRKr3hB8C9Ir8lcWa4PYf56OwLeOieU3Y9G1HYiy-N0AvZzAN2dgJBnwWq-HKM5Bo9atsos8_FnXfHOJlXgLjjB_ZKaNfBt8rZTIOQad2x0YEbiSLOjj99sHRmbH_");


        mvc.perform(get(url).contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().size(1))
            .andExpect(model().attribute("meta", equalTo(expected)));
    }


}
