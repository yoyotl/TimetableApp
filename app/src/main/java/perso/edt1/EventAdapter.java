package perso.edt1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        AbsListView.LayoutParams params = (AbsListView.LayoutParams) convertView.getLayoutParams();
        assert event != null;
        params.height = 100 * Integer.parseInt(CalendarUtils.formattedHours(event.getEndTime())) - Integer.parseInt(CalendarUtils.formattedHours(event.getStartTime()));
        convertView.setLayoutParams(params);

        TextView module = convertView.findViewById(R.id.event1Module);
        TextView room = convertView.findViewById(R.id.event1Room);
        TextView prof = convertView.findViewById(R.id.event1Prof);
        TextView group = convertView.findViewById(R.id.event1Group);
        TextView notes = convertView.findViewById(R.id.event1Notes);
        TextView timeView = convertView.findViewById(R.id.event1Time);

        module.setText(event.getModule());

        String time = CalendarUtils.formattedShortTime(event.getStartTime()) + " - " + CalendarUtils.formattedShortTime(event.getEndTime());
        timeView.setText(time);

        ArrayList<String> eventRooms =  event.getRoom();
        String eventRoom = "";
        for (int i = 0; i < eventRooms.size(); i++) {
            eventRoom += eventRooms.get(i);
            if (i != eventRooms.size() - 1)
                eventRoom += ", ";
        }
        room.setText(eventRoom);

        ArrayList<String> eventProfs = event.getTeacher();
                String eventProf = "";
        for (int i = 0; i < eventProfs.size(); i++) {
            eventProf += eventProfs.get(i);
            if (i != eventProfs.size() - 1)
                eventProf += ", ";
        }
        prof.setText(eventProf);

        ArrayList<String> eventGroups = event.getGroup();
                String eventGroup = "";
        for (int i = 0; i < eventGroups.size(); i++) {
            eventGroup += eventGroups.get(i);
            if (i != eventGroups.size() - 1)
                eventGroup += ", ";
        }
        group.setText(eventGroup);

        notes.setText(event.getNotes());

        return convertView;
    }
}
