/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++){
            if (this.users[i].getName().equals(name)){
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        // checking to see if network full
        if (userCount == users.length){
            return false;
        }
        // checking to see if user is already in the network
        if (getUser(name) != null){
            return false;
        }
        // otherwise we add the user 
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User u1 = getUser(name1);
        User u2 = getUser(name2);
        
        // checking if both users belongs
        if (u1 == null || u2 == null){
            return false;
        }
        // make name1 follow name2
        return u1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        // Get the user by name
        User user = getUser(name);
        if (user == null){
            return null;
        }

        // Highest number of mutual connections found so far
        int maxMutualCount = -1;

        // Name of the recommended user to follow
        String recommendation = null;

        // Go over all users in the network
        for (int i = 0; i < userCount; i++){
            User candidate = users[i];

            // Check that the user does not already follow the candidate
            // and that the candidate is not the same user
            if (!user.follows(candidate.getName()) && user != candidate){

                // Count mutual connections between user and candidate
                int mutual = user.countMutual(candidate);

                // Update recommendation if this candidate has more mutual connections
                if (mutual > maxMutualCount){
                    maxMutualCount = mutual;
                    recommendation = candidate.getName();
                }
            }
    }
    // Return the recommended user name (or null if none found)
    return recommendation;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int bestCount = -1;
        String mostPopular = null;

        // Go over all users as candidates
        for (int i = 0; i < userCount; i++){
            String candidate = users[i].getName();
            int count = 0;

            // Count how many users follow this candidate
            for (int j = 0; j < userCount; j++){
                if (users[j].follows(candidate)){
                    count++;
                }
            }

            // If this candidate is more popular, update the result
            if (count > bestCount){
                bestCount = count;
                mostPopular = candidate;
            }
    }
            // Return the most popular user's name
            return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;

        // Go over all users in the network
        for (int i = 0; i < userCount; i++){
            // Check if this user follows the given name
            if (users[i].follows(name)){
                count++;
            }
        }
        // Return how many users follow this name
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        // Create the network string
        String networkStr = "Network:";
        
        if (userCount > 0){
            networkStr = networkStr + "\n";
        }

        // Go over all users in the network
        for (int i = 0; i < userCount; i++){
            User currentUser = users[i];

            // Add the user's information to the string
            // Do not add a new line after the last user
            if (userCount - i == 1){
                networkStr = networkStr + currentUser.toString();
            } else {
                networkStr = networkStr + currentUser.toString() + "\n";
            }
        }

        // Return the final network description
        return networkStr;
    }
}