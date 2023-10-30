package perso.edt1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DailyEventAdapter extends ArrayAdapter<HourEvent> {

    private int RelativeLayoutWidth;

    public DailyEventAdapter(@NonNull Context context, ArrayList<HourEvent> hourEvents) {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        LayoutInflater inflaterCell = LayoutInflater.from(getContext());



        ViewGroup.LayoutParams MainRelativeLayoutParams = convertView.getLayoutParams();
        RelativeLayoutWidth = MainRelativeLayoutParams.width;

        setEvents(convertView, event.events);

        return convertView;
    }

    private void setEvents(View convertView, ArrayList<Event> events)
    {
        LinearLayout event1 = convertView.findViewById(R.id.event1);
        LinearLayout event2 = convertView.findViewById(R.id.event2);
        LinearLayout event3 = convertView.findViewById(R.id.event3);
        LinearLayout event4 = convertView.findViewById(R.id.event4);

        Log.d("DailyEventAdapter", "setEvents: " + events.size());

        if(events.size() == 0)
        {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
            hideEvent(event4);
        }
        else if(events.size() == 1)
        {
            int perEventWidth = RelativeLayoutWidth - 70;
            setEvent(convertView, event1, events.get(0),1, perEventWidth);
            hideEvent(event2);
            hideEvent(event3);
            hideEvent(event4);
        }
        else if(events.size() == 2)
        {
            int perEventWidth = (RelativeLayoutWidth - 70) / 2;
            setEvent(convertView, event1, events.get(0),1, perEventWidth);
            setEvent(convertView, event2, events.get(1),2, perEventWidth);
            hideEvent(event3);
            hideEvent(event4);
        }
        else if(events.size() == 3)
        {
            int perEventWidth = (RelativeLayoutWidth - 70) / 3;
            setEvent(convertView, event1, events.get(0),1, perEventWidth);
            setEvent(convertView, event2, events.get(1),2, perEventWidth);
            setEvent(convertView, event3, events.get(2),3, perEventWidth);
            hideEvent(event4);
        }
        else        //TODO: Limiter à 4 events max
        {
            int perEventWidth = (RelativeLayoutWidth - 70) / 4;
            setEvent(convertView, event1, events.get(0),1, perEventWidth);
            setEvent(convertView, event2, events.get(1),2, perEventWidth);
            setEvent(convertView, event3, events.get(2),3, perEventWidth);
            setEvent(convertView, event4, events.get(3),4, perEventWidth);
        }
    }

    private void setEvent(View convertView, LinearLayout eventCell, Event event, int eventNb, int perEventWidth)
    {
        int moduleId = getContext().getResources().getIdentifier("event" + eventNb + "Module", "id", getContext().getPackageName());
        TextView eventModule = convertView.findViewById(moduleId);
        int roomId = getContext().getResources().getIdentifier("event" + eventNb + "Room", "id", getContext().getPackageName());
        TextView eventRoom = convertView.findViewById(roomId);
        int teacherId = getContext().getResources().getIdentifier("event" + eventNb + "Prof", "id", getContext().getPackageName());
        TextView eventTeacher = convertView.findViewById(teacherId);
        int groupId = getContext().getResources().getIdentifier("event" + eventNb + "Group", "id", getContext().getPackageName());
        TextView eventGroup = convertView.findViewById(groupId);
        int noteId = getContext().getResources().getIdentifier("event" + eventNb + "Notes", "id", getContext().getPackageName());
        TextView eventNote = convertView.findViewById(noteId);

        String eventCategory = event.getCategory().replaceAll("\\s+", "_");

        int color = getContext().getResources().getIdentifier(eventCategory, "color", getContext().getPackageName());
        if(color != 0) {
            eventCell.setBackgroundColor(getContext().getColor(color));
        }

        ViewGroup.LayoutParams layoutParameters = eventCell.getLayoutParams();
        layoutParameters.width = perEventWidth;
        layoutParameters.height = 150 * Integer.parseInt(CalendarUtils.formattedHours(event.getEndTime())) - Integer.parseInt(CalendarUtils.formattedHours(event.getStartTime()));
        eventCell.setLayoutParams(layoutParameters);


        eventModule.setText(event.getModule());

        ArrayList<String> eventRooms = event.getRoom();
        String StringEventRoom = "";
        for (int i = 0; i < eventRooms.size(); i++) {
            StringEventRoom += eventRooms.get(i);
            if (i != eventRooms.size() - 1)
                StringEventRoom += ", ";
        }
        eventRoom.setText(StringEventRoom);

        ArrayList<String> eventProfs = event.getTeacher();
        String StringEventProf = "";
        for (int i = 0; i < eventProfs.size(); i++) {
            StringEventProf += eventProfs.get(i);
            if (i != eventProfs.size() - 1)
                StringEventProf += ", ";
        }
        eventTeacher.setText(StringEventProf);

        ArrayList<String> eventGroups = event.getGroup();
        String StringEventGroup = "";
        for (int i = 0; i < eventGroups.size(); i++) {
            StringEventGroup += eventGroups.get(i);
            if (i != eventGroups.size() - 1)
                StringEventGroup += ", ";
        }
        eventGroup.setText(StringEventGroup);

        eventNote.setText(event.getNotes());

        eventCell.setVisibility(View.VISIBLE);
    }

    private void hideEvent(LinearLayout eventCell)
    {
        eventCell.setVisibility(View.GONE);
    }
}