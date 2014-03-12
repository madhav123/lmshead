package org.mifosplatform.portfolio.message.data;
@SuppressWarnings("unused")
public class MediaEnumoptionData {
	
	private final String id;
    private final String code;
    private final String value;
    public MediaEnumoptionData(final String id, final String code, final String value) {
        this.id = id;
        this.code = code;
        this.value = value;
    }

    public String getId() {
        return id;
    }

	public String getValue() {
		return value;
	}
}
