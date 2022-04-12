import React from "react";
import { createRoot } from 'react-dom/client';
import {CustomTabs} from "./customTabs";

const container = document.getElementById('root');
const root = createRoot(container);

root.render(<CustomTabs/>)
