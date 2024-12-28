import { Button, OutlinedInput } from "@mui/material";

export default function SearchBox() {
  return (
    <div style={{ display: "flex", flexDirection: "column" }}>
      <div style={{ display: "flex" }}>
        <OutlinedInput
          placeholder="Search for parking lots"
          fullWidth
          sx={{ borderRadius: 2 }}
        />
        <Button variant="contained" sx={{ borderRadius: 2 }}>
          Search
        </Button>
      </div>
    </div>
  );
}
