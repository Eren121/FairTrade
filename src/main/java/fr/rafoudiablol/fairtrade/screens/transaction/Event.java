package fr.rafoudiablol.fairtrade.screens.transaction;

/**
 * Event inside trading GUI
 * It's a sort of events inside bukkit events.
 * It's to not overload Bukkit's events, because slots many slots, about tens of them, can register. It's not big
 * but it keep components safe.
 */
public enum Event {
    OPEN_GUI,
    TOGGLE_CONFIRMATION,
    UPDATE_OFFER
}
