package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.common.Gender;

import java.util.Set;

public record CharacterInfo(
  int characterLevel,
  Gender gender,
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
  Set<Long> learnedSpellIds,
  String hearthstoneLocation
) {
}
