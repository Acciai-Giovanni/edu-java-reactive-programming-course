package it.gacciai.sec12;

import it.gacciai.common.Util;
import it.gacciai.sec12.assignment.SlackMember;
import it.gacciai.sec12.assignment.SlackRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08SlackAssignment {

    private static final Logger log = LoggerFactory.getLogger(Lec08SlackAssignment.class);

    public static void main(String[] args) {
        var room = new SlackRoom("reactor");
        var sam = new SlackMember("sam");
        var jake = new SlackMember("jake");
        var mike = new SlackMember("mike");

        // add 2 members
        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi all..");
        Util.sleepSeconds(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");
        Util.sleepSeconds(4);

        room.addMember(mike);
        mike.says("Hey guys..glad to be here...");
    }

}
