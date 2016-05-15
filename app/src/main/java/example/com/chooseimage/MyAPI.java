package example.com.chooseimage;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by dilip on 4/29/16.
 */
public interface MyAPI {
    @FormUrlEncoded
    @POST("/exceutive_login")
    void login( @Field("email_id") String email_id, @Field("password") String password, Callback<User> callback);
    @FormUrlEncoded
    @POST("/edit_profile")
    void userEditProfile1( @Field("user_id") String user_id,@Field("base_64_string") String base_64_string, Callback<User> callback);
}
