package com.example.cloneplaystore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class App implements Parcelable {


    @SerializedName("histogram")
    @Expose
    private Histogram histogram;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("descriptionHTML")
    @Expose
    private String descriptionHTML;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("installs")
    @Expose
    private String installs;
    @SerializedName("minInstalls")
    @Expose
    private Long minInstalls;
    @SerializedName("maxInstalls")
    @Expose
    private Long maxInstalls;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("scoreText")
    @Expose
    private String scoreText;
    @SerializedName("ratings")
    @Expose
    private Long ratings;
    @SerializedName("reviews")
    @Expose
    private Long reviews;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("free")
    @Expose
    private Boolean free;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("priceText")
    @Expose
    private String priceText;
    @SerializedName("offersIAP")
    @Expose
    private Boolean offersIAP;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("androidVersion")
    @Expose
    private String androidVersion;
    @SerializedName("androidVersionText")
    @Expose
    private String androidVersionText;
    @SerializedName("developer")
    @Expose
    private String developer;
    @SerializedName("developerId")
    @Expose
    private String developerId;
    @SerializedName("developerEmail")
    @Expose
    private String developerEmail;
    @SerializedName("developerWebsite")
    @Expose
    private String developerWebsite;
    @SerializedName("developerAddress")
    @Expose
    private String developerAddress;
    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;
    @SerializedName("developerInternalID")
    @Expose
    private String developerInternalID;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("genreId")
    @Expose
    private String genreId;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("headerImage")
    @Expose
    private String headerImage;
    @SerializedName("screenshots")
    @Expose
    private List<String> screenshots;
    @SerializedName("contentRating")
    @Expose
    private String contentRating;
    @SerializedName("adSupported")
    @Expose
    private Boolean adSupported;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("updated")
    @Expose
    private Long updated;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("recentChanges")
    @Expose
    private String recentChanges;
    @SerializedName("comments")
    @Expose
    private List<String> comments;
    @SerializedName("editorsChoice")
    @Expose
    private Boolean editorsChoice;
    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("url")
    @Expose
    private String url;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInstalls() {
        return installs;
    }

    public void setInstalls(String installs) {
        this.installs = installs;
    }

    public Long getMinInstalls() {
        return minInstalls;
    }

    public void setMinInstalls(Long minInstalls) {
        this.minInstalls = minInstalls;
    }

    public Long getMaxInstalls() {
        return maxInstalls;
    }

    public void setMaxInstalls(Long maxInstalls) {
        this.maxInstalls = maxInstalls;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getScoreText() {
        return scoreText;
    }

    public void setScoreText(String scoreText) {
        this.scoreText = scoreText;
    }

    public Long getRatings() {
        return ratings;
    }

    public void setRatings(Long ratings) {
        this.ratings = ratings;
    }

    public Long getReviews() {
        return reviews;
    }

    public void setReviews(Long reviews) {
        this.reviews = reviews;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPriceText() {
        return priceText;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public Boolean getOffersIAP() {
        return offersIAP;
    }

    public void setOffersIAP(Boolean offersIAP) {
        this.offersIAP = offersIAP;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAndroidVersionText() {
        return androidVersionText;
    }

    public void setAndroidVersionText(String androidVersionText) {
        this.androidVersionText = androidVersionText;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public String getDeveloperWebsite() {
        return developerWebsite;
    }

    public void setDeveloperWebsite(String developerWebsite) {
        this.developerWebsite = developerWebsite;
    }

    public String getDeveloperAddress() {
        return developerAddress;
    }

    public void setDeveloperAddress(String developerAddress) {
        this.developerAddress = developerAddress;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getDeveloperInternalID() {
        return developerInternalID;
    }

    public void setDeveloperInternalID(String developerInternalID) {
        this.developerInternalID = developerInternalID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public Boolean getAdSupported() {
        return adSupported;
    }

    public void setAdSupported(Boolean adSupported) {
        this.adSupported = adSupported;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRecentChanges() {
        return recentChanges;
    }

    public void setRecentChanges(String recentChanges) {
        this.recentChanges = recentChanges;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Boolean getEditorsChoice() {
        return editorsChoice;
    }

    public void setEditorsChoice(Boolean editorsChoice) {
        this.editorsChoice = editorsChoice;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Histogram getHistogram() {
        return histogram;
    }

    public void setHistogram(Histogram histogram) {
        this.histogram = histogram;
    }





    protected App(Parcel in) {
        title = in.readString();
        description = in.readString();
        descriptionHTML = in.readString();
        summary = in.readString();
        installs = in.readString();
        minInstalls = in.readByte() == 0x00 ? null : in.readLong();
        maxInstalls = in.readByte() == 0x00 ? null : in.readLong();
        score = in.readByte() == 0x00 ? null : in.readDouble();
        scoreText = in.readString();
        ratings = in.readByte() == 0x00 ? null : in.readLong();
        reviews = in.readByte() == 0x00 ? null : in.readLong();
        price = in.readByte() == 0x00 ? null : in.readInt();
        byte freeVal = in.readByte();
        free = freeVal == 0x02 ? null : freeVal != 0x00;
        currency = in.readString();
        priceText = in.readString();
        byte offersIAPVal = in.readByte();
        offersIAP = offersIAPVal == 0x02 ? null : offersIAPVal != 0x00;
        size = in.readString();
        androidVersion = in.readString();
        androidVersionText = in.readString();
        developer = in.readString();
        developerId = in.readString();
        developerEmail = in.readString();
        developerWebsite = in.readString();
        developerAddress = in.readString();
        privacyPolicy = in.readString();
        developerInternalID = in.readString();
        genre = in.readString();
        genreId = in.readString();
        icon = in.readString();
        headerImage = in.readString();
        if (in.readByte() == 0x01) {
            screenshots = new ArrayList<>();
            in.readList(screenshots, String.class.getClassLoader());
        } else {
            screenshots = null;
        }
        contentRating = in.readString();
        byte adSupportedVal = in.readByte();
        adSupported = adSupportedVal == 0x02 ? null : adSupportedVal != 0x00;
        released = in.readString();
        updated = in.readByte() == 0x00 ? null : in.readLong();
        version = in.readString();
        recentChanges = in.readString();
        if (in.readByte() == 0x01) {
            comments = new ArrayList<>();
            in.readList(comments, String.class.getClassLoader());
        } else {
            comments = null;
        }
        byte editorsChoiceVal = in.readByte();
        editorsChoice = editorsChoiceVal == 0x02 ? null : editorsChoiceVal != 0x00;
        appId = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(descriptionHTML);
        dest.writeString(summary);
        dest.writeString(installs);
        if (minInstalls == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(minInstalls);
        }
        if (maxInstalls == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(maxInstalls);
        }
        if (score == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(score);
        }
        dest.writeString(scoreText);
        if (ratings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(ratings);
        }
        if (reviews == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(reviews);
        }
        if (price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(price);
        }
        if (free == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (free ? 0x01 : 0x00));
        }
        dest.writeString(currency);
        dest.writeString(priceText);
        if (offersIAP == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (offersIAP ? 0x01 : 0x00));
        }
        dest.writeString(size);
        dest.writeString(androidVersion);
        dest.writeString(androidVersionText);
        dest.writeString(developer);
        dest.writeString(developerId);
        dest.writeString(developerEmail);
        dest.writeString(developerWebsite);
        dest.writeString(developerAddress);
        dest.writeString(privacyPolicy);
        dest.writeString(developerInternalID);
        dest.writeString(genre);
        dest.writeString(genreId);
        dest.writeString(icon);
        dest.writeString(headerImage);
        if (screenshots == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(screenshots);
        }
        dest.writeString(contentRating);
        if (adSupported == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adSupported ? 0x01 : 0x00));
        }
        dest.writeString(released);
        if (updated == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(updated);
        }
        dest.writeString(version);
        dest.writeString(recentChanges);
        if (comments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(comments);
        }
        if (editorsChoice == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (editorsChoice ? 0x01 : 0x00));
        }
        dest.writeString(appId);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };
}