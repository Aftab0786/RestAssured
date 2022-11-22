package com.bridgelabz.spotify;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyApi {
	public String token;
	String user_id;
	String playlist_id;
	String tracks;
	@BeforeTest
	public void getToken() {
		token = "Bearer BQCp-kcYSa11M22wDewkaGBUI9tHQH0vaF2W26LvYuyMiBVXiz_jTMo09IVa9CUnJknWWkbwHrFL9mvxFZgayNJwOSR5XJGJ9J80DzEhl_gPp1Nd-iXDDcW-ixs5jbYU5sII2pzi5bHpmqhdKvBNa34q3pcsa8l1zLJNxKPsVG-U9t8qLQspFQ-ziZv212DNOmQAwNp3xTll-spaWz_xGvKDyz310NVIc--E5--dhMiQcbzb33TvGlxpKTbc9vG1KSHOEwpVWj3xXkIPtgc";
	}
	@BeforeTest
	public void getTracks() {
		tracks = "spotify:track:2TuNVD5u5iAslHp0moLlTn";
	}
	@Test (priority = 0)
	public void testGet_CurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		user_id = response.path("id");
		System.out.print("User Id Is:"+user_id);
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 1)
	public void testGet_UsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("	https://api.spotify.com/v1/users/"+user_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 2)
	public void createPlaylist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"Aftab Playlist\",\r\n"
						+ "  \"description\": \"New playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.post("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		playlist_id = response.path("id");
		System.out.print("Playlist Id Is:"+playlist_id);
		response.then().assertThat().statusCode(201);
	}
	@Test (priority = 3)
	public void add_Items_to_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("uris", tracks)
				.when()
				.post("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	@Test (priority = 4)
	public void remove_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{ \"tracks\": [{ \"uri\": \"spotify:track:4yR2z3JafnCo86THA6ISZ4\" }] }")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 5)
	public void get_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 6)
	public void get_Users_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 7)
	public void get_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 8)
	public void get_Current_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 9)
	public void change_Playlist_Details() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"name\": \"Updated Playlist Aftab\",\r\n"
						+ "  \"description\": \"Updated playlist description\",\r\n"
						+ "  \"public\": false\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 10)
	public void search_For_Item() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParams("q", "kumar sanu")
				.queryParams("type", "track")
				.when()
				.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 11)
	public void update_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\r\n"
						+ "  \"range_start\": 0,\r\n"
						+ "  \"insert_before\": 3,\r\n"
						+ "  \"range_length\": 2\r\n"
						+ "}")
				.when()
				.put("https://api.spotify.com/v1/playlists/1F04YMFpdR8Ttx8T16H9V6/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 12)
	public void get_Tracks_Audio_Analysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/4yR2z3JafnCo86THA6ISZ4");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 13)
	public void get_Tracks_Audio_Features() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 14)
	public void get_Track_Audio_Features() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/audio-features/4yR2z3JafnCo86THA6ISZ4");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 15)
	public void get_Several_Tracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks?ids=4yR2z3JafnCo86THA6ISZ4,2TuNVD5u5iAslHp0moLlTn");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 16)
	public void get_Track() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks/4yR2z3JafnCo86THA6ISZ4");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	@Test (priority = 17)
	public void get_Playlist_CoverImage() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
}
