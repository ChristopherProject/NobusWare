package QuarantineAPI;

import QuarantineAPI.config.bus.PubSub;
import QuarantineAPI.config.configuration.BusConfigurations;
import QuarantineAPI.config.configuration.config.impl.BusPubSubConfiguration;
import QuarantineAPI.config.message.scan.MessageScanner;
import QuarantineAPI.config.message.scan.impl.MethodAndFieldBasedMessageScanner;

public class EventAPI {
	
	private static final PubSub<Event> EVENT_PUBSUB = new PubSub((new BusConfigurations.Builder()).setConfiguration(BusPubSubConfiguration.class, () -> {BusPubSubConfiguration busPubSubConfiguration = BusPubSubConfiguration.getDefault();busPubSubConfiguration.setScanner((MessageScanner) new MethodAndFieldBasedMessageScanner());return busPubSubConfiguration;}).build());;

	public static PubSub<Event> getLWJEB() {
		return EVENT_PUBSUB;
	}

	public static void put(Object o) {
		getLWJEB().subscribe(o);
	}

	public static void remove(Object o) {
		getLWJEB().unsubscribe(o);
	}

	public static void fire(Event event) {
		getLWJEB().post(event).dispatch();
	}
}
