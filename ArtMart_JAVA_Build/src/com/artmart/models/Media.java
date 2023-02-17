package com.artmart.models;

public class Media {

    private int id;
    private String file_name;
    private String file_type;
    private String file_path;
    private int blog_id;

    public Media(int id, String file_name, String file_type, String file_path, int blog_id) {
        this.id = id;
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_path = file_path;
        this.blog_id = blog_id;
    }

    public Media(String file_name, String file_type, String file_path, int blog_id) {
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_path = file_path;
        this.blog_id = blog_id;
    }

    public Media(String file_name, String file_type, String file_path) {
        this.file_name = file_name;
        this.file_type = file_type;
        this.file_path = file_path;
    }

    public int getId() {
        return id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    @Override
    public String toString() {
        return "Media{" + "id=" + id + ", file_name=" + file_name + ", file_type=" + file_type + ", file_path=" + file_path + ", blog_id=" + blog_id + '}';
    }

}
