package org.lab.samples.mongo.api.search;

import lombok.Data;

@Data
public class ContractSearchOptions {

	private String agreementId;

	private String holderIdCard;

	private String recipientIdCard;

}
