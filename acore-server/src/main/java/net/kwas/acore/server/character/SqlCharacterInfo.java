package net.kwas.acore.server.character;

public record SqlCharacterInfo(
  int characterLevel,
  int gender,
  long spirit,
  long attackPower,
  long rangedAttackPower,
  long spellPower,
  float mainWeaponMinDamage,
  float mainWeaponMaxDamage,
  float mainWeaponBaseMinDamage,
  float mainWeaponBaseMaxDamage,
  float mainWeaponSpeed,
  boolean isMainWeaponTwoHanded,
  int hearthstoneLocation
) {
}
