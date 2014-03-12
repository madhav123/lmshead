package org.mifosplatform.portfolio.message.data;

public class MessageTypeEnumaration {

	public static MediaEnumoptionData enummessageData(final int id) {
		return enummessageData(EnumMessageType.fromInt(id));
	}

	public static MediaEnumoptionData enummessageData(final EnumMessageType mediaEnum) {

		
		MediaEnumoptionData optionData = null;
		switch (mediaEnum) {
		case EMAIL:
			optionData = new MediaEnumoptionData(EnumMessageType.EMAIL.getValue(), EnumMessageType.EMAIL.getCode(), "EMAIL");
			break;
		
		case Message:
			optionData = new MediaEnumoptionData(EnumMessageType.Message.getValue(), EnumMessageType.Message.getCode(), "MESSAGE");
			break;

	}
		return optionData;
  }
}
