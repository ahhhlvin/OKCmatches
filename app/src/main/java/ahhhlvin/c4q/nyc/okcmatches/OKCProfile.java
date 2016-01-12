package ahhhlvin.c4q.nyc.okcmatches;

/**
 * Created by alvin2 on 1/11/16.
 */
public class OKCProfile {

    private String imageURL;
    private String username;
    private int age;
    private String location;
    private int matchPercentage;

    // FOR TESTING
//    public OKCProfile(String imageUrl, String username, int age, String location, int matchPercentage) {
//        this.imageURL = imageUrl;
//        this.username = username;
//        this.age = age;
//        this.location = location;
//        this.matchPercentage = matchPercentage;
//    }

    public OKCProfile (){};

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
}
