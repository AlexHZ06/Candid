
function Radial({ active, onClick, text }) {
    const isActive = Array.isArray(active)
        ? active.includes(text)
        : active === text;

    return (
        <div>
            <button onClick={onClick} className={`

                border
                border-neutral-400
                w-fit
                pl-3
                pr-3
                    rounded-xl
                    whitespace-nowrap
                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    ${isActive
                        ? "bg-amber-500 border-white text-white shadow-[0_0px_10px_rgba(0,0,0,0.25)]"
                        : ""}
            `}
            >
                {text}
            </button>
        </div>
    );
}

export default Radial

