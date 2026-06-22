package com.z_tech.CMS.DTO;

import java.util.UUID;

import com.z_tech.CMS.Models.element;

public class DTO_element {
   private element e;

    public class NewElementObj extends element{
        public String title = e.title;
        public String descritption = e.description;
        public UUID grpahix= e.graphix;
    }
}
