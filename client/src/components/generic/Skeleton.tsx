interface SkeletonProps {
    className?: string;
}

export const Skeleton = ({className, ...props}: SkeletonProps) => {
    return (
        <div
            className={`animate-pulse rounded bg-zinc-700/50 ${className}`}
            {...props}
        />
    );
};
