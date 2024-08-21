package org.onecode.filemanagementapi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedCourseResponse {
    private String message;

    public CreatedCourseResponse(String message) {
        this.message = message;
    }


}