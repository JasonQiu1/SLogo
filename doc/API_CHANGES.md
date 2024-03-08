# API Changes

#### Deprecated loadSession command

In the changes stage of the project, we realized that implementing session reassignment violated the
dependencies that we had created between listeners and controllers. Listeners hold the session, but
calling loadSession in the controller required the session to be reassigned from the controller side
of the relationship. Therefore, we felt it made the most sense to deprecate the loadSession method
that returned a session. Having the method return the commands allowed us to
maintain backward compatibility while encouraging a more cohesive design where controllers do not
reassign the session. 
