package hello.real_world.domain.tag.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseTags {

    public ResponseTags() {
    }

    public ResponseTags(List<String> tags) {
        this.tags = tags;
    }

    private List<String> tags;

}
