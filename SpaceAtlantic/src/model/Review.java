package model;

import java.time.LocalDate;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private int guestId;

    public Review(int reviewId, int rating, String comment, LocalDate reviewDate, int guestId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.guestId = guestId;
    }

    public int getReviewId() { return reviewId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDate getReviewDate() { return reviewDate; }
    public int getGuestId() { return guestId; }

    public String getSummary() {
        return "Review #" + reviewId + " by Guest " + guestId + " - Rating: " + rating + "/5 - '" + comment + "'";
    }
}
