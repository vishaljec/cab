package com.hcs.uimanager;

/**
 * The super-interface for all of the screen classes. All screen interfaces
 * should extend this interface in order not to use the most generic methods
 * and not to have code replication into the application implementation.
 */
public interface Screen {

    /**
     * Get logic item that represents the screen into the application. It can be
     * for example a java me Screen or an Android Activity.
     * @return Object the screen view active container item.
     */
    public Object getUiScreen();

}
