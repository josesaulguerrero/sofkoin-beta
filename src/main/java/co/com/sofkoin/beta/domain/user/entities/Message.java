package co.com.sofkoin.beta.domain.user.entities;

import co.com.sofka.domain.generic.Entity;
import co.com.sofkoin.beta.domain.common.values.CryptoSymbol;
import co.com.sofkoin.beta.domain.common.values.identities.UserID;
import co.com.sofkoin.beta.domain.market.values.identities.MarketID;
import co.com.sofkoin.beta.domain.user.values.MessageRelationTypes;
import co.com.sofkoin.beta.domain.user.values.MessageStatus;
import co.com.sofkoin.beta.domain.user.values.ProposalCryptoAmount;
import co.com.sofkoin.beta.domain.user.values.ProposalCryptoPrice;
import co.com.sofkoin.beta.domain.user.values.identities.MessageID;
import lombok.ToString;

@ToString
public class Message extends Entity<MessageID> {
    private final MarketID marketID;
    private final ProposalCryptoAmount proposalCryptoAmount;

    private final ProposalCryptoPrice proposalCryptoPrice;

    private final UserID senderId;

    private final UserID receiverId;

    private final MessageRelationTypes messageRelationType;

    private final CryptoSymbol cryptoSymbol;
    private MessageStatus messageStatus;

    public Message(MessageID entityId, MarketID marketID, ProposalCryptoAmount proposalCryptoAmount,
                   ProposalCryptoPrice proposalCryptoPrice, MessageStatus messageStatus,
                   UserID senderId, UserID receiverId, MessageRelationTypes messageRelationTypes, CryptoSymbol cryptoSymbol) {
        super(entityId);
        this.marketID = marketID;
        this.proposalCryptoAmount = proposalCryptoAmount;
        this.proposalCryptoPrice = proposalCryptoPrice;
        this.messageStatus = messageStatus;
        this.senderId = senderId;
        this.messageRelationType = messageRelationTypes;
        this.receiverId = receiverId;
        this.cryptoSymbol = cryptoSymbol;
    }

    public void changeStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public ProposalCryptoAmount proposalCryptoAmount() {
        return proposalCryptoAmount;
    }

    public ProposalCryptoPrice proposalCryptoPrice() {
        return proposalCryptoPrice;
    }

    public MessageStatus messageStatus() {
        return messageStatus;
    }

    public UserID senderId() {
        return senderId;
    }

    public MarketID marketID() {
        return marketID;
    }

    public UserID receiverId() {
        return receiverId;
    }

    public CryptoSymbol cryptoSymbol() {
        return cryptoSymbol;
    }
}
