package com.coffeearmy.librarian.events;

/**
 * @see http://nick.perfectedz.com/otto-event-system/
 */
public abstract class AbstractEvent
{
    private Enum _type;
 
    protected AbstractEvent(Enum type)
    {
        this._type = type;
    }
 
    public Enum getType()
    {
        return this._type;
    }
}
