package fightfriends.artifacts;

import fightfriends.FixedDie;
import fightfriends.friends.Friend;
import fightfriends.friends.FriendFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeashTest {
    // friendPower = health + attack + speed
    // capture threshold = leashPower / (leashPower + friendPower) * 100
    // A d100 roll succeeds when roll <= threshold.
    private final ArtifactFactory artifactFactory = new ArtifactFactory();
    private final FriendFactory friendFactory = new FriendFactory();

    @Test
    void capturesAtThresholdAndFailsAboveIt() {
        Friend friend = friendFactory.createFox("Fox", 30, 10, 10);
        Leash success = artifactFactory.createLeash("Leash", 50, new FixedDie(50));
        Leash failure = artifactFactory.createLeash("Leash", 50, new FixedDie(51));
        assertTrue(success.tryToCaptureFriend(friend));
        assertFalse(failure.tryToCaptureFriend(friend));
    }

    @Test
    void weakeningFriendImprovesCaptureChance() {
        Friend friend = friendFactory.createFox("Fox", 80, 10, 10);
        Leash leash = artifactFactory.createLeash("Leash", 50, new FixedDie(50));
        assertFalse(leash.tryToCaptureFriend(friend));
        friend.setHealth(30);
        assertTrue(leash.tryToCaptureFriend(friend));
    }

    @Test
    void strongerLeashImprovesCaptureChance() {
        Friend friend = friendFactory.createFox("Fox", 80, 10, 10);
        assertFalse(artifactFactory.createLeash("Weak", 50, new FixedDie(50)).tryToCaptureFriend(friend));
        assertTrue(artifactFactory.createLeash("Strong", 100, new FixedDie(50)).tryToCaptureFriend(friend));
    }

    @Test
    void rejectsKnockedOutFriendsAndZeroCapturePower() {
        Friend friend = friendFactory.createFox("Fox", 30, 10, 10);
        assertFalse(artifactFactory.createLeash("Weak", 0, new FixedDie(1)).tryToCaptureFriend(friend));
        friend.setHealth(0);
        assertFalse(artifactFactory.createLeash("Leash", 50, new FixedDie(1)).tryToCaptureFriend(friend));
    }

    @Test
    void factoryPreservesNameAndPower() {
        Leash leash = artifactFactory.createLeash("Basic", 50);
        assertEquals("Basic", leash.getName());
        assertEquals(50, leash.getCapturePower());
        assertEquals(50, leash.getPower());
    }
}
