{
  description = "IntelliJ IDEA development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
      };
      
      fhs = pkgs.buildFHSEnv {
        name = "intellij-fhs";
        targetPkgs = pkgs: with pkgs; [
          jetbrains.idea
          jdk21
          git
          maven
          gradle
          zlib
          ncurses
          libGL
          xorg.libX11
          xorg.libXext
          xorg.libXrender
          xorg.libXi
          xorg.libXtst
          stdenv.cc.cc.lib
        ];
        # Убираем жесткий запуск, просто входим в bash
        runScript = "bash"; 
      };
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        LD_LIBRARY_PATH = "${pkgs.stdenv.cc.cc.lib}/lib";
        
        buildInputs = [ fhs ];
        shellHook = ''
          echo "✅ FHS среда готова."
          echo "1. Введи 'intellij-fhs' чтобы войти в окружение."
          echo "2. Внутри введи 'idea-ultimate' или 'idea-community'."
          echo "   (Если не сработает, введи 'ls /usr/bin/ | grep idea')"
        '';
      };

	};

}
