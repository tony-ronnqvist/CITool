import { useContext } from "react";

import BuildsContext from "./buildsContext";

export const useBuilds = () => {
  const builds = useContext(BuildsContext);
  return builds;
};

export const useBuild = id => {
  const { builds, loading } = useBuilds();

  return {
    build: builds ? builds[id] : null,
    loading
  };
};
