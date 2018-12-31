package com.desafiolatam.desafioface.background;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.desafiolatam.desafioface.networks.users.GetUsers;

import java.util.HashMap;
import java.util.Map;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecentUserService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RECENT_USERS = "com.desafiolatam.desafioface.background.action.ACTION_RECENT_USERS";
    public static final String USERS_FINISHED = "com.desafiolatam.desafioface.background.USERS_FINISHED";

    public RecentUserService() {
        super("RecentUserService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionRecentUsers(Context context) {
        Intent intent = new Intent(context, RecentUserService.class);
        intent.setAction(ACTION_RECENT_USERS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RECENT_USERS.equals(action)) {
                fetchUsers();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void fetchUsers() {
        Map<String, String> queryMap = new HashMap<>();
        //Starting from page number 1
        queryMap.put("page", "1");
        new FetchUsers(3, this).execute(queryMap);
    }

    //Background class for method access;
    private class FetchUsers extends GetUsers {


        public FetchUsers(int additionalPages, Context context) {
            super(additionalPages, context);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Log.d("RESULT", String.valueOf(integer));
            //Broadcast
            Intent intent = new Intent();
            intent.setAction(USERS_FINISHED);
            //Here is broadcasting
            LocalBroadcastManager.getInstance(RecentUserService.this).sendBroadcast(intent);

        }
    }
}
