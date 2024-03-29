/*
Copyright (C) 2004 Geoffrey Alan Washburn
    
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
    
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
    
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
USA.
*/
   
/**
 * {@link ClientEvent} encapsulates events corresponding to actions {@link Client}s may take.   
 * @author Geoffrey Washburn &lt;<a href="mailto:geoffw@cis.upenn.edu">geoffw@cis.upenn.edu</a>&gt;
 * @version $Id: ClientEvent.java 359 2004-01-31 20:14:31Z geoffw $
 */

public class ClientEvent {
        /* Internals ******************************************************/
        
        /**
         * Internal representations of events.
         */
        private static final int MOVE_FORWARD = 0;
        private static final int MOVE_BACKWARD = 1;
        private static final int TURN_LEFT = 2;
        private static final int TURN_RIGHT = 3;
        private static final int FIRE = 4;
        private static final int UPDATE_PROJECTILE = 5;
        
        /**
         * Default to 0, to be invalid.
         */
        private final int event;
        
        /**
         * Create a new {@link ClientEvent} from an internal representation.
         */
        private ClientEvent(int event) {
                assert((event >= 0) && (event <= 4));
                this.event = event;
        }

        /* Public data ****************************************************/
        
        /** 
         * Generated when a {@link Client} moves forward.
         */
        public static final ClientEvent moveForward = new ClientEvent(MOVE_FORWARD);

        /**
         * Generated when a {@link Client} moves backward.
         */
        public static final ClientEvent moveBackward = new ClientEvent(MOVE_BACKWARD);

        /**
         * Generated when a {@link Client} turns left.
         */
        public static final ClientEvent turnLeft = new ClientEvent(TURN_LEFT);

        /**
         * Generated when a {@link Client} turns right.
         */
        public static final ClientEvent turnRight = new ClientEvent(TURN_RIGHT);

        /**
         * Generated when a {@link Client} fires.
         */
        public static final ClientEvent fire = new ClientEvent(FIRE);

        /**
         * Generated when a {@link Client} fires.
         */
        public static final ClientEvent updateProjectile = new ClientEvent(UPDATE_PROJECTILE);       
        
        
}
