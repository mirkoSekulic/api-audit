package com.audit.apiaudit.modules.users.error.exception;


import com.audit.apiaudit.modules.shared.error.exception.entity.EntityDoesNotExistException;

public class UserNotFoundException extends EntityDoesNotExistException
{
    private static final long serialVersionUID = -363437578101642142L;

    public UserNotFoundException(Object entityIdentificator)
    {
        super("user", entityIdentificator);
    }
}
