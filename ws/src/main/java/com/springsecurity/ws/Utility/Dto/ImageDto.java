package com.springsecurity.ws.Utility.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto implements Serializable {
    private static final long serialVersionUID = 654001020269160181L;

    private String idBrowserPhoto;

    private String title;

    private String imagePath;

    private String imageFileName;

}
