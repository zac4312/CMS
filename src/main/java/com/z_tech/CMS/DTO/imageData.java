package com.z_tech.CMS.DTO;

import java.util.UUID;

import com.z_tech.CMS.Models.element_graphix;

public class imageData {
    element_graphix eg;

    public String img_path;
    public UUID graphix_id;

    public imageData(){}
    public imageData(String img_path, UUID gID) {
        this.img_path = img_path;
        this.graphix_id = gID;
    }
}
