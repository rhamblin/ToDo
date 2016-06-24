package encloode.todo;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Riko on 2016-06-21.
 */
public class ToDoApplication extends Application {

public void onCreate() {
    super.onCreate();

    Firebase.setAndroidContext(this);
}
}
