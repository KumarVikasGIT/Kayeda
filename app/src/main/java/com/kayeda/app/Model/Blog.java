
package com.kayeda.app.Model;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Blog {

    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
