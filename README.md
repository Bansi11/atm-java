# ATM System — Java (GAB en Java)

Simulation en console d'un distributeur automatique bancaire (ATM), développée en Java à partir d'une modélisation UML complète (cas d'utilisation, classes, séquence), avec une couche de sécurité : hachage du PIN et authentification à deux facteurs (OTP).

> Projet pédagogique réalisé dans le cadre de mon cursus en Cybersécurité (ICT University, Yaoundé) — objectif : relier conception logicielle (UML) et bonnes pratiques de sécurité de base.

## Statut

🚧 **En cours de développement** — projet réalisé étape par étape, en apprenant Java.

## Fonctionnalités prévues

- [ ] Authentification par carte + PIN
- [ ] **PIN haché** (jamais stocké/comparé en clair)
- [ ] **OTP (One-Time Password)** — confirmation par code à usage unique après le PIN
- [ ] Consultation de solde
- [ ] Dépôt (espèces / chèque)
- [ ] Retrait d'espèces
- [ ] Transfert entre comptes
- [ ] Impression de reçu (simulée en console)
- [ ] Journal des transactions

## Conception (UML)

Le système a d'abord été modélisé avant d'être codé :
- Diagramme de cas d'utilisation
- Diagramme de classes
- Diagramme de séquence (Retrait d'espèces)

*(diagrammes disponibles dans le dossier `/docs`)*

## Aspect sécurité

Ce projet met l'accent sur deux pratiques de sécurité de base souvent négligées dans les projets étudiants :

1. **Hachage du PIN** — le PIN n'est jamais stocké ni comparé en texte brut ; il est haché (avec sel) avant toute comparaison, comme dans un vrai système bancaire.
2. **OTP / 2FA** — après validation du PIN, un code à usage unique est généré et doit être saisi pour confirmer la transaction, illustrant l'authentification à deux facteurs.

## Stack technique

- **Langage :** Java
- **Concepts :** Programmation Orientée Objet, hachage cryptographique (`MessageDigest`), génération de codes OTP

## Auteur

**Simo Bansi Junior Désiré**
Étudiant en Cybersécurité — ICT University, Yaoundé, Cameroun

## Licence

MIT
