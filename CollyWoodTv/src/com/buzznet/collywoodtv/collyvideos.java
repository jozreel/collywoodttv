package com.buzznet.collywoodtv;

public class collyvideos {
	private String title;
	private String Excerpt;
	private String ImgUrl;
	private String Producer;
	private String country;
	private String cat;
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return Excerpt;
	}
	public void setExcerpt(String excerpt) {
		Excerpt = excerpt;
	}
	public String getImgUrl() {
		return ImgUrl;
	}
	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}
	public String getProducer() {
		return Producer;
	}
	public void setProducer(String producer) {
		Producer = producer;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public collyvideos(String img, String Excerpt,String title,String caty,String Producer,String Country) {
		this.country=Country;
		this.Producer = Producer;
		this.ImgUrl = img;
		this.title = title;
		this.Excerpt = Excerpt;
		this.cat =caty;
	}

}
