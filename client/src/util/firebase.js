import { useEffect } from "react";
import firebase from "firebase/app";
import "firebase/firestore";

const config = {
  apiKey: process.env.REACT_APP_API_KEY,
  authDomain: process.env.REACT_APP_AUTH_DOMAIN,
  databaseURL: process.env.REACT_APP_DATABASE_URL,
  projectId: process.env.REACT_APP_PROJECT_ID,
  storageBucket: process.env.REACT_APP_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_APP_ID,
  measurementId: process.env.REACT_APP_MEASUREMENT_ID
};

class Firebase {
  constructor() {
    firebase.initializeApp(config);
    this.db = firebase.firestore(firebase);
  }

  subscribe = cb =>
    this.db.collection("builds").onSnapshot(ss => cb(this.reduceSnapshot(ss)));

  reduceSnapshot = ss => {
    const newList = [...ss.docs].sort((a, b) =>
      this.comparefunc(
        a.data().data.buildResult.timestamp,
        b.data().data.buildResult.timestamp
      )
    );
    return newList.reduce((acc, curr) => {
      acc[curr.id] = { ...curr.data(), id: curr.id };
      return acc;
    }, {});
  };

  comparefunc = (a, b) => {
    return a.localeCompare(b) * -1;
  };

  getBuilds = () =>
    this.db
      .collection("builds")
      .get()
      .then(this.reduceSnapshot)
      .catch(err => console.error(err));
}

export default new Firebase();
