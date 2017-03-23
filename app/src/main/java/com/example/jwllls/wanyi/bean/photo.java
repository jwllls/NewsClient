package com.example.jwllls.wanyi.bean;

import java.util.List;

/**
 * Created by jwllls on 2016/12/7.
 */

public class photo {
    private boolean statue;
    private int total;
    private List<buty> tngou;

    public static class buty {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int galleryclass;//          图片分类
        private String title;//          标题
        private String img;//          图库封面
        private int count;//          访问数
        private int rcount;//           回复数
        private int fcount;//          收藏数
        private int size;//      图片多少张

        public int getGalleryclass() {
            return galleryclass;
        }

        public void setGalleryclass(int galleryclass) {
            this.galleryclass = galleryclass;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<buty> getTngou() {
        return tngou;
    }

    public void setTngou(List<buty> tngou) {
        this.tngou = tngou;
    }
}
