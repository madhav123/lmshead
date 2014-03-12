package org.mifosplatform.portfolio.message.service;
import java.util.List;
import org.mifosplatform.portfolio.message.data.BillingMessageData;
public interface LeaseMesssageReadPlatformService {
	void processingMessages();

	void processNotify();
}