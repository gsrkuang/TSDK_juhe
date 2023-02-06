package com.sdk.sdklibrary.mvp.model;

/**
 * Date:2023-01-18
 * Time:16:52
 * author:colin
 */
public class MVPChangePasswordBean {
     String oldPassword;
     String newPassword;
     String newConfirmPassword;

    public MVPChangePasswordBean(String oldPassword, String newPassword, String newConfirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newConfirmPassword = newConfirmPassword;
    }

    @Override
    public String toString() {
        return "MVPChangePasswordBean{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newConfirmPassword='" + newConfirmPassword + '\'' +
                '}';
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewConfirmPassword() {
        return newConfirmPassword;
    }

    public void setNewConfirmPassword(String newConfirmPassword) {
        this.newConfirmPassword = newConfirmPassword;
    }
}
