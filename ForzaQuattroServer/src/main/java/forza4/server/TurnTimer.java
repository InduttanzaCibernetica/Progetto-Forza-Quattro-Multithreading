
package forza4.server;

import java.util.Timer;
import java.util.TimerTask;

public class TurnTimer {

    private Timer timer;

    public void start(GameRoom room, long dueMillis) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                room.onTimeout(); // scaduto il tempo, notifica la stanza
            }
        }, dueMillis);
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
