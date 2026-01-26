{ pkgs ? import <nixpkgs> { } }:

pkgs.mkShell {
  buildInputs = with pkgs; [ nodejs_20 ];

  shellHook = ''
    echo "Welcome to the Angular Dev Environment on NixOS!"
    node --version
  '';
}
