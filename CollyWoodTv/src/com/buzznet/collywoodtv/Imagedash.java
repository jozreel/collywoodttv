package com.buzznet.collywoodtv;
import android.graphics.Bitmap;

public class Imagedash {
		String urlimg;
		Bitmap thumbimg;
		String cat;
		String vidUrl;
		String desc;
		String pid;
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getVidUrl() {
			return vidUrl;
		}
		public void setVidUrl(String vidUrl) {
			this.vidUrl = vidUrl;
		}
		public String getCat() {
			return cat;
		}
		public void setCat(String cat) {
			this.cat = cat;
		}
		public Imagedash() {
			// TODO Auto-generated constructor stub
		}
		public String getUrlimg() {
			return urlimg;
		}
		public void setUrlimg(String urlimg) {
			this.urlimg = urlimg;
		}
		public Bitmap getThumbimg() {
			return thumbimg;
		}
		public void setThumbimg(Bitmap thumbimg) {
			this.thumbimg = thumbimg;
		}
		
	}