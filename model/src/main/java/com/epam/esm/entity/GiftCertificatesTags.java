package com.epam.esm.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class GiftCertificatesTags extends Entity{

    private static final long serialVersionUID = 1L;

    private long giftCertificateId;
    private long tagId;

    public GiftCertificatesTags() {
    }

    public GiftCertificatesTags(long giftCertificateId, long tagId) {
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificatesTags that = (GiftCertificatesTags) o;
        return giftCertificateId == that.giftCertificateId &&
                tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificateId, tagId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GiftCertificatesTags.class.getSimpleName() + "[", "]")
                .add("giftCertificateId=" + giftCertificateId)
                .add("tagId=" + tagId)
                .toString();
    }
}
