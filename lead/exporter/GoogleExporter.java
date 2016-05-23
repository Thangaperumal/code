package com.erp.lead.exporter;

/*import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201509.cm.Operator;
import com.google.api.ads.adwords.axis.v201509.rm.AdwordsUserListServiceInterface;
import com.google.api.ads.adwords.axis.v201509.rm.CrmBasedUserList;
import com.google.api.ads.adwords.axis.v201509.rm.MutateMembersOperand;
import com.google.api.ads.adwords.axis.v201509.rm.MutateMembersOperandDataType;
import com.google.api.ads.adwords.axis.v201509.rm.MutateMembersOperation;
import com.google.api.ads.adwords.axis.v201509.rm.MutateMembersReturnValue;
import com.google.api.ads.adwords.axis.v201509.rm.UserList;
import com.google.api.ads.adwords.axis.v201509.rm.UserListOperation;
import com.google.api.ads.adwords.axis.v201509.rm.UserListReturnValue;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;*/

/**
 * This example adds a remarketing user list (a.k.a. audience) and uploads hashed email addresses
 * to populate the list.
 *
 * <p>
 * <em>Note:</em> It may take up to several hours for the list to be populated with members.
 * Email addresses must be associated with a Google account.
 * For privacy purposes, the user list size will show as zero until the list has at least 1,000
 * members. After that, the size will be rounded to the two most significant digits.
 * </p>
 *
 * <p>
 * <p>Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 * </p>
 */
public class GoogleExporter {
/*
  /*private static final ImmutableList<String> EMAILS =
      ImmutableList.of("client1@example.com", "client2@example.com", " Client3@example.com ");
  private static final MessageDigest digest = getSHA256MessageDigest();

  public static void main(String[] args) throws Exception {
    // Generate a refreshable OAuth2 credential.
    Credential oAuth2Credential = new OfflineCredentials.Builder()
        .forApi(Api.ADWORDS)
        .fromFile()
        .build()
        .generateCredential();

    // Construct an AdWordsSession.
    AdWordsSession session = new AdWordsSession.Builder()
        .fromFile()
        .withOAuth2Credential(oAuth2Credential)
        .build();

    AdWordsServices adWordsServices = new AdWordsServices();

    //runExample(adWordsServices, session);
  }

  public static void export(List<String> emails) throws Exception {
	// Generate a refreshable OAuth2 credential.
	    Credential oAuth2Credential = new OfflineCredentials.Builder()
	        .forApi(Api.ADWORDS)
	        .fromFile()
	        .build()
	        .generateCredential();

	    // Construct an AdWordsSession.
	    AdWordsSession session = new AdWordsSession.Builder()
	        .fromFile()
	        .withOAuth2Credential(oAuth2Credential)
	        .build();

	    AdWordsServices adWordsServices = new AdWordsServices();
    // Get the UserListService.
    AdwordsUserListServiceInterface userListService =
        adWordsServices.get(session, AdwordsUserListServiceInterface.class);

    // Create remarketing user list.
    CrmBasedUserList userList = new CrmBasedUserList();
    userList.setName("Customer relationship management list #" + System.currentTimeMillis());
    userList.setDescription("A list of customers that originated from email addresses");

    // See limit here: https://support.google.com/adwords/answer/6276125#requirements.
    userList.setMembershipLifeSpan(30L);

    // This field is required. It links to a service you created that allows members of this list
    // to remove themselves. It will be shown in the "Why This Ad" pop-up as a courtesy to users
    // and so it needs to be verified.
    // Read more about "Why This Ad" here: https://support.google.com/ads/answer/2662850.
    userList.setOptOutLink("http://endpoint1.example.com/optout");

    // Create operation.
    UserListOperation operation = new UserListOperation();
    operation.setOperand(userList);
    operation.setOperator(Operator.ADD);

    // Add user list.
    UserListReturnValue result = userListService.mutate(new UserListOperation[] { operation });

    // Display user list.
    UserList userListAdded = result.getValue(0);
    System.out.printf("User list with name '%s' and ID '%d' was added.%n",
        userListAdded.getName(), userListAdded.getId());

    // Get user list ID.
    Long userListId = userListAdded.getId();

    // Create operation to add members to the user list based on email addresses.
    MutateMembersOperation mutateMembersOperation = new MutateMembersOperation();
    MutateMembersOperand operand = new MutateMembersOperand();
    operand.setUserListId(userListId);

    // You can optionally provide this field.
    operand.setDataType(MutateMembersOperandDataType.EMAIL_SHA256);

    // Hash normalized email addresses based on SHA-256 hashing algorithm.
    List<String> emailHashes = Lists.newArrayListWithCapacity(emails.size());
    for (String email : emails) {
      String normalizedEmail = toNormalizedEmail(email);
      emailHashes.add(toSHA256String(normalizedEmail));
    }

    // Add email address hashes.
    operand.setMembers(emailHashes.toArray(new String[emailHashes.size()]));
    mutateMembersOperation.setOperand(operand);
    mutateMembersOperation.setOperator(Operator.ADD);

    // Add members to the user list based on email addresses.
    MutateMembersReturnValue mutateMembersResult =
        userListService.mutateMembers(new MutateMembersOperation[] { mutateMembersOperation });

    // Display results.
    // Reminder: it may take several hours for the list to be populated with members.
    for (UserList userListResult : mutateMembersResult.getUserLists()) {
      System.out.printf("%d email addresses were uploaded to user list with name '%s' and ID '%d' "
          + "and are scheduled for review.%n",
          emails.size(), userListResult.getName(), userListResult.getId());
    }
  }*/

  /**
   * Hash a string using SHA-256 hashing algorithm.
   *
   * @param str the string to hash.
   * @return the SHA-256 hash string representation.
   * @throws UnsupportedEncodingException If UTF-8 charset is not supported.
   */
	/*
  private static String toSHA256String(String str) throws UnsupportedEncodingException {
    byte[] hash = digest.digest(str.getBytes("UTF-8"));
    StringBuilder result = new StringBuilder();
    for (byte b : hash) {
      result.append(String.format("%02x", b));
    }

    return result.toString();
  }*/

  /**
   * Removes leading and trailing whitespace and converts all characters to lower case.
   *
   * @param email the email address to normalize.
   * @return a normalized copy of the string.
   */
  /*private static String toNormalizedEmail(String email) {
    return email.trim().toLowerCase();
  }*/

  /** Returns SHA-256 hashing algorithm. */
  /*private static MessageDigest getSHA256MessageDigest() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Missing SHA-256 algorithm implementation.", e);
    }
  }
  */
}