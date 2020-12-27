package eu.f1nn.model.fe2;

/**
 * Created by Finn on 27.12.2020.
 */
public class AlarmData {
    private String keyword;
    private String keyword_description;
    private String keyword_category;
    private String keyword_color;

    public AlarmData() {

    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword_description() {
        return keyword_description;
    }

    public void setKeyword_description(String keyword_description) {
        this.keyword_description = keyword_description;
    }

    public String getKeyword_category() {
        return keyword_category;
    }

    public void setKeyword_category(String keyword_category) {
        this.keyword_category = keyword_category;
    }

    public String getKeyword_color() {
        return keyword_color;
    }

    public void setKeyword_color(String keyword_color) {
        this.keyword_color = keyword_color;
    }
}