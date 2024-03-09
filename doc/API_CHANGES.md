# API Changes

#### Deprecated loadSession command in XMLConfiguration

In the changes stage of the project, we realized that implementing session reassignment violated the
dependencies that we had created between listeners and controllers. Listeners hold the session, but
calling loadSession in the controller required the session to be reassigned from the controller side
of the relationship. Therefore, we felt it made the most sense to deprecate the loadSession method
that returned a session. Having the method return the commands allowed us to
maintain backward compatibility while encouraging a more cohesive design where controllers do not
reassign the session.

#### Added getPreferences Command in XMLConfiguration

In the change stage of the project, the XML preferences requirement required us to create
functionality for loading preferences from an XML file. This was a completely new function for the
XmlConfiguration class and required a new public method to maintain model/view separation. The view
needed to know the established preferences to create the correct UI environment, but the actual XML
parsing was done in the model. Updating our API by adding a single method allowed us to maintain
this separation.
