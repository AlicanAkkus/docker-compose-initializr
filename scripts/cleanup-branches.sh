#!/usr/bin/env bash

read -p "Are you ready to delete all local branches except master, and delete unused tracking branches from your local (y/n)?" CONT
if [ "$CONT" = "y" ]; then
    git branch | grep -v master | xargs git branch -D
    git fetch origin --prune
else
  echo "Skipped. See you next time.";
fi

