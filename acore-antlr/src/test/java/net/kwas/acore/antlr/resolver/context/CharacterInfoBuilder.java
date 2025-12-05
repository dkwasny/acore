package net.kwas.acore.antlr.resolver.context;

import net.kwas.acore.common.Gender;

import java.util.Set;

/**
 * This builder was generated with IntelliJ's Replace Constructor with Builder functionality.
 */
public class CharacterInfoBuilder {
  private int characterLevel;
  private Gender gender;
  private long spirit;
  private long attackPower;
  private long rangedAttackPower;
  private long spellPower;
  private float mainWeaponMinDamage;
  private float mainWeaponMaxDamage;
  private float mainWeaponBaseMinDamage;
  private float mainWeaponBaseMaxDamage;
  private float mainWeaponSpeed;
  private boolean isMainWeaponTwoHanded;
  private Set<Long> learnedSpellIds;
  private String hearthstoneLocation;

  public CharacterInfoBuilder characterLevel(int characterLevel) {
    this.characterLevel = characterLevel;
    return this;
  }

  public CharacterInfoBuilder gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  public CharacterInfoBuilder spirit(long spirit) {
    this.spirit = spirit;
    return this;
  }

  public CharacterInfoBuilder attackPower(long attackPower) {
    this.attackPower = attackPower;
    return this;
  }

  public CharacterInfoBuilder rangedAttackPower(long rangedAttackPower) {
    this.rangedAttackPower = rangedAttackPower;
    return this;
  }

  public CharacterInfoBuilder spellPower(long spellPower) {
    this.spellPower = spellPower;
    return this;
  }

  public CharacterInfoBuilder mainWeaponMinDamage(float mainWeaponMinDamage) {
    this.mainWeaponMinDamage = mainWeaponMinDamage;
    return this;
  }

  public CharacterInfoBuilder mainWeaponMaxDamage(float mainWeaponMaxDamage) {
    this.mainWeaponMaxDamage = mainWeaponMaxDamage;
    return this;
  }

  public CharacterInfoBuilder mainWeaponBaseMinDamage(float mainWeaponBaseMinDamage) {
    this.mainWeaponBaseMinDamage = mainWeaponBaseMinDamage;
    return this;
  }

  public CharacterInfoBuilder mainWeaponBaseMaxDamage(float mainWeaponBaseMaxDamage) {
    this.mainWeaponBaseMaxDamage = mainWeaponBaseMaxDamage;
    return this;
  }

  public CharacterInfoBuilder mainWeaponSpeed(float mainWeaponSpeed) {
    this.mainWeaponSpeed = mainWeaponSpeed;
    return this;
  }

  public CharacterInfoBuilder isMainWeaponTwoHanded(boolean isMainWeaponTwoHanded) {
    this.isMainWeaponTwoHanded = isMainWeaponTwoHanded;
    return this;
  }

  public CharacterInfoBuilder learnedSpellIds(Set<Long> learnedSpellIds) {
    this.learnedSpellIds = learnedSpellIds;
    return this;
  }

  public CharacterInfoBuilder hearthstoneLocation(String hearthstoneLocation) {
    this.hearthstoneLocation = hearthstoneLocation;
    return this;
  }

  public CharacterInfo createCharacterInfo() {
    return new CharacterInfo(
      characterLevel,
      gender,
      spirit,
      attackPower,
      rangedAttackPower,
      spellPower,
      mainWeaponMinDamage,
      mainWeaponMaxDamage,
      mainWeaponBaseMinDamage,
      mainWeaponBaseMaxDamage,
      mainWeaponSpeed,
      isMainWeaponTwoHanded,
      learnedSpellIds,
      hearthstoneLocation
    );
  }
}
